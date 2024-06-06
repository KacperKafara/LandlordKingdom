import { FC } from "react";
import { useTranslation } from "react-i18next";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { useForm, SubmitHandler } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Form, FormField, FormItem, FormLabel, FormControl, FormMessage } from "@/components/ui/form";
import { useAddLocal } from "@/data/useAddLocal";
import {TFunction} from "i18next";

const addLocalSchema = (t: TFunction) => z.object({
    name: z.string().min(1, { message: t("addLocalPage.wrong.name") }),
    description: z.string().min(1, { message: t("addLocalPage.wrong.description") }),
    size: z.number().min(1, { message: t("addLocalPage.wrong.size") }),
    address: z.object({
        number: z.string().min(1, { message: t("addLocalPage.wrong.number") }),
        street: z.string().min(1, { message: t("addLocalPage.wrong.street") }),
        city: z.string().min(1, { message: t("addLocalPage.wrong.city") }),
        zip: z.string().min(1, { message: t("addLocalPage.wrong.zip") }),
        country: z.string().min(1, { message: t("addLocalPage.wrong.country") }),
    }),
    marginFee: z.number().min(0, t("addLocalPage.wrong.marginFee")),
    rentalFee: z.number().min(0, t("addLocalPage.wrong.rentalFee")),
});

type AddLocalFormData = z.infer<ReturnType<typeof addLocalSchema>>;

const AddLocalForm: FC = () => {
    const { t } = useTranslation();
    const { addLocal } = useAddLocal();
    const form = useForm<AddLocalFormData>({
        resolver: zodResolver(addLocalSchema(t)),
    });

    const onSubmit: SubmitHandler<AddLocalFormData> = async (data) => {
        try {
            await addLocal({
                name: data.name,
                description: data.description,
                size: data.size,
                address: {
                    number: data.address.number,
                    street: data.address.street,
                    city: data.address.city,
                    zip: data.address.zip,
                    country: data.address.country
                },
                marginFee: data.marginFee,
                rentalFee: data.rentalFee,
            });
        } catch (error) {
            console.error('An error occurred while adding local:', error);
        }
    };

    return (
        <div className="flex w-full justify-center">
            <div className="flex w-10/12 flex-col gap-2 pb-6">
                <Card className="mt-3 flex justify-center">
                    <CardHeader>
                        <CardTitle>{t("addLocalPage.title")}</CardTitle>
                    </CardHeader>
                </Card>
                <Card className="relative mt-3">
                    <CardContent>
                        <Form {...form}>
                            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
                                <FormField
                                    control={form.control}
                                    name="name"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>{t("addLocalPage.name")}</FormLabel>
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
                                            <FormLabel>{t("addLocalPage.description")}</FormLabel>
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
                                            <FormLabel>{t("addLocalPage.size")}</FormLabel>
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
                                <FormField
                                    control={form.control}
                                    name="address.number"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>{t("addLocalPage.number")}</FormLabel>
                                            <FormControl>
                                                <Input {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="address.street"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>{t("addLocalPage.street")}</FormLabel>
                                            <FormControl>
                                                <Input {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="address.city"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>{t("addLocalPage.city")}</FormLabel>
                                            <FormControl>
                                                <Input {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="address.zip"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>{t("addLocalPage.zip")}</FormLabel>
                                            <FormControl>
                                                <Input {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="address.country"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>{t("addLocalPage.country")}</FormLabel>
                                            <FormControl>
                                                <Input {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="marginFee"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>{t("addLocalPage.marginFee")}</FormLabel>
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
                                <FormField
                                    control={form.control}
                                    name="rentalFee"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>{t("addLocalPage.rentalFee")}</FormLabel>
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
                                <Button type="submit">{t("addLocalPage.formSubmit")}</Button>
                            </form>
                        </Form>
                    </CardContent>
                </Card>
            </div>
        </div>
    );
};

export default AddLocalForm;
