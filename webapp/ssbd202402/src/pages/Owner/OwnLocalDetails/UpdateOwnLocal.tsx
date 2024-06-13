import { FC } from "react";
import { useTranslation } from "react-i18next";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Form, FormField, FormItem, FormLabel, FormControl, FormMessage } from "@/components/ui/form";
import { useUpdateLocalData } from "@/data/local/useMutateOwnLocalUpdate"
import {TFunction} from "i18next";
import {toast} from "@/components/ui/use-toast.ts";
import {useGetOwnLocalDetails} from "@/data/local/useGetOwnLocalDetails.ts";
import {useParams} from "react-router-dom";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuTrigger
} from "@/components/ui/dropdown-menu.tsx";
import RefreshQueryButton from "@/components/RefreshQueryButton.tsx";
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import ConfirmDialog from "@/components/ConfirmDialog.tsx";

const updateLocalDetailsSchema = (t: TFunction) => z.object({
    id: z.string(),
    name: z.string()
        .min(1, { message: t("updateLocalPage.wrong.name") })
        .max(200, { message: t("updateLocalPage.wrong.name") }),
    description: z.string()
        .min(1, { message: t("updateLocalPage.wrong.description") })
        .max(5000, { message: t("updateLocalPage.wrong.description") }),
    state: z.enum(["WITHOUT_OWNER", "UNAPPROVED", "ACTIVE", "ARCHIVED", "INACTIVE", "RENTED", ""], {
        errorMap: () => ({ message: t("updateLocalPage.wrong.state") })
    })
});

type UpdateLocalFormData = z.infer<ReturnType<typeof updateLocalDetailsSchema>>;

const UpdateLocalDetailsForm: FC = () => {
    const { id } = useParams<{ id: string }>();
    const { t } = useTranslation();
    const { data } = useGetOwnLocalDetails(id!);
    const mutateAsync  = useUpdateLocalData();
    const form = useForm<UpdateLocalFormData>({
        resolver: zodResolver(updateLocalDetailsSchema(t)),
        values: {
            id: id || "",
            name: data?.data.name || "",
            description: data?.data.description || "",
            state: data?.data.state || "",
        },
    });

    const updateLocalData = form.handleSubmit((request ) => {
            let etag: string = data?.headers.etag;
            if (!etag) {
                toast({
                    variant: "destructive",
                    title: t("updateLocalPage.errorTitle"),
                })
                return;
            }
            etag = etag.substring(1, etag.length - 1);
            mutateAsync.mutate({request, etag});
        }
    );

    const stateToTranslationKey = (state: string) => {
        switch (state) {
            case "WITHOUT_OWNER":
                return "updateLocalPage.states.withoutOwner";
            case "UNAPPROVED":
                return "updateLocalPage.states.unapproved";
            case "ACTIVE":
                return "updateLocalPage.states.active";
            case "INACTIVE":
                return "updateLocalPage.states.inactive";
            case "RENTED":
                return "updateLocalPage.states.rented";
            case "ARCHIVED":
                return "updateLocalPage.states.archived";
            default:
                return "updateLocalPage.states.unknown";
        }
    };

    return (
        <Card className="relative">
            <CardHeader>
                <CardTitle className="text-center">
                    {t("ownLocalDetails.updateData")}
                </CardTitle>
            </CardHeader>
            <CardContent className="flex justify-center">
                <Form {...form}>
                    <form onSubmit={updateLocalData} className="flex w-3/4 flex-col gap-3">
                        <FormField
                            control={form.control}
                            name="name"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>{t("updateLocalPage.name")}</FormLabel>
                                    <FormControl>
                                        <Input {...field} />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="description"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>{t("updateLocalPage.description")}</FormLabel>
                                    <FormControl>
                                        <Input {...field} />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="state"
                            render={({ field }) => (
                                <FormItem>
                                    <div>
                                        <FormLabel className="mr-4">{
                                            data?.data.state
                                                ? t("updateLocalPage.state") + ": " + t(stateToTranslationKey(data?.data.state))
                                                : t("updateLocalPage.state") + ": " + t("updateLocalPage.states.unknown")
                                        }</FormLabel>
                                        {(data?.data.state === "ACTIVE" || data?.data.state === "INACTIVE") && (
                                            <div>
                                                <FormLabel className="mt-4">{t("updateLocalPage.changeState")}</FormLabel>
                                                <FormControl>
                                                    <DropdownMenu>
                                                        <DropdownMenuTrigger>
                                                            <Button variant="outline" className="ml-2">
                                                                {t(stateToTranslationKey(form.getValues('state')))}
                                                            </Button>
                                                        </DropdownMenuTrigger>
                                                        <DropdownMenuContent>
                                                            <DropdownMenuItem onClick={() => field.onChange("ACTIVE")}>
                                                                {t("updateLocalPage.states.active")}
                                                            </DropdownMenuItem>
                                                            <DropdownMenuItem onClick={() => field.onChange("INACTIVE")}>
                                                                {t("updateLocalPage.states.inactive")}
                                                            </DropdownMenuItem>
                                                        </DropdownMenuContent>
                                                    </DropdownMenu>
                                                </FormControl>
                                            </div>
                                        )}
                                    </div>
                                </FormItem>
                            )}
                        />

                        <Button type="button" variant="outline" onClick={() => form.reset()}>
                            {t("updateLocalPage.reset")}
                        </Button>
                        <ConfirmDialog
                            className="mt-5"
                            buttonText={t("common.update")}
                            dialogTitle={t("common.confirmDialogTitle")}
                            dialogDescription={t("updateLocalPage.confirmDialogDescription")}
                            confirmAction={() => updateLocalData()}
                        />
                    </form>
                </Form>
            </CardContent>
            <RefreshQueryButton
                className="absolute right-1 top-1"
                queryKeys={["ownLocalDetails"]}
            />
        </Card>
    );
};

export default UpdateLocalDetailsForm;
