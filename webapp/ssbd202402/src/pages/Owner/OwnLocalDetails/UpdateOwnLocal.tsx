// src/components/UpdateLocalDetailsForm.tsx
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { Button } from "@/components/ui/button";
import { CardFooter } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Form, FormField, FormItem, FormLabel, FormControl, FormMessage } from "@/components/ui/form";
import { useUpdateLocalData } from "@/data/local/useMutateOwnLocalUpdate"
import {TFunction} from "i18next";

interface UpdateLocalDetailsFormProps {
    id: string;
    initialName: string;
    initialDescription: string;
    initialSize: number;
}

const updateLocalDetailsSchema = (t: TFunction) => z.object({
    name: z.string().min(1, { message: t("updateLocalPage.wrong.name") }),
    description: z.string().min(1, { message: t("updateLocalPage.wrong.description") }),
    size: z.number().min(1, { message: t("updateLocalPage.wrong.size") }),
});

type UpdateLocalFormData = z.infer<ReturnType<typeof updateLocalDetailsSchema>>;

const UpdateLocalDetailsForm: FC<UpdateLocalDetailsFormProps> = ({
                                                                     id,
                                                                     initialName,
                                                                     initialDescription,
                                                                     initialSize,
                                                                 }) => {
    const { t } = useTranslation();
    const { mutateAsync } = useUpdateLocalData();
    const form = useForm<UpdateLocalFormData>({
        resolver: zodResolver(updateLocalDetailsSchema(t)),
        defaultValues: {
            name: initialName,
            description: initialDescription,
            size: initialSize,
        },
    });

    const updateLocalData = form.handleSubmit(
        async (data: UpdateLocalFormData) => {
            const formattedData = {
                id,
                name: data.name,
                description: data.description,
                size: data.size
            };
            await mutateAsync(formattedData);
            form.reset();
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
                <CardFooter className="flex justify-end space-x-2">
                    <Button type="button" variant="outline" onClick={() => form.reset()}>
                        {t("updateLocalPage.reset")}
                    </Button>
                    <Button type="submit" onClick={() => updateLocalData()}>{t("updateLocalPage.submit")}</Button>
                </CardFooter>
            </form>
        </Form>
    );
};

export default UpdateLocalDetailsForm;
