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

const updateLocalDetailsSchema = (t: TFunction) => z.object({
    id: z.string(),
    name: z.string().min(1, { message: t("updateLocalPage.wrong.name") }),
    description: z.string().min(1, { message: t("updateLocalPage.wrong.description") }),
    size: z.number().min(1, { message: t("updateLocalPage.wrong.size") }),
});

type UpdateLocalFormData = z.infer<ReturnType<typeof updateLocalDetailsSchema>>;

const UpdateLocalDetailsForm: FC = () => {
    const { id } = useParams<{ id: string }>();
    const { t } = useTranslation();
    const { data } = useGetOwnLocalDetails(id!);
    const mutateAsync  = useUpdateLocalData();
    const form = useForm<UpdateLocalFormData>({
        resolver: zodResolver(updateLocalDetailsSchema(t)),
        defaultValues: {
            id: id,
            name: data?.data.name,
            description: data?.data.description,
            size: data?.data.size,
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

    return (
        <Form {...form}>
            <form onSubmit={updateLocalData} className="flex flex-col gap-3">
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
                    name="size"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>{t("updateLocalPage.size")}</FormLabel>
                            <FormControl>
                                <Input
                                    {...field}
                                    type="number"
                                    onChange={(event) => {
                                        const value = parseFloat(event.target.value);
                                        field.onChange(value);
                                    }}
                                />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <div>
                    <Button type="button" variant="outline" onClick={() => form.reset()}>
                        {t("updateLocalPage.reset")}
                    </Button>
                    <Button type="submit">{t("updateLocalPage.submit")}</Button>
                </div>
            </form>
        </Form>
    );
};

export default UpdateLocalDetailsForm;
