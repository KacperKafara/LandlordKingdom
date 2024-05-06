import {FC} from "react";
import {useMeQuery, usMeMutation} from "@/data/meQueries.ts";
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form";
import { z } from "zod"
import {SubmitHandler, useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Input} from "@/components/ui/input.tsx";
import {cn} from "@/lib/utils.ts";
import {Button, buttonVariants} from "@/components/ui/button.tsx";
import {UserUpdateRequestType} from "@/types/user/UserUpdateRequestType.ts";
import {useTranslation} from "react-i18next";
import { TFunction } from "i18next";
import {Toaster} from "@/components/ui/toaster.tsx";



const userDataFormSchema =(t: TFunction) =>  z.object(
    {
        firstName: z.string().max(50).min(1, t("userDataPage.firstNameNotEmpty")),
        lastName: z.string().max(50).min(1, t("userDataPage.lastNameNotEmpty")),
        language: z.string().regex(/^(en-US|pl)$/)
    }
)

type userDataFormValues = z.infer<ReturnType<typeof  userDataFormSchema>>

const UserDataPage : FC = () => {
    const {data} = useMeQuery();
    const putMutation = usMeMutation();
    const {t} = useTranslation();
    const form = useForm<userDataFormValues>(
        {resolver: zodResolver(userDataFormSchema(t)),
            values: {
                firstName: data?.firstName || "",
                lastName: data?.lastName || "",
                language: data?.language || "",
            }
        }
    )



    const handleUserSubmit: SubmitHandler<UserUpdateRequestType> = (data) => {
        putMutation.mutate(data)
    }
    return (
        <>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(handleUserSubmit)}>
                    <FormField
                        control={form.control}
                        name="firstName"
                        render={({field}) => (
                            <FormItem className="my-3">
                                <FormLabel>{t("userDataPage.firstName")} </FormLabel>
                                <FormControl>
                                   <Input {...field}/>
                                </FormControl>
                                {/*<FormDescription> Description </FormDescription>*/}
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="lastName"
                        render={({field}) => (
                            <FormItem className="mb-3">
                                <FormLabel>{t("userDataPage.lastName")} </FormLabel>
                                <FormControl>
                                    <Input  {...field}/>
                                </FormControl>
                                {/*<FormDescription> Description </FormDescription>*/}
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="language"
                        render={({field}) => (
                            <FormItem >
                                <FormLabel>{t("userDataPage.language")}</FormLabel>
                                <div>
                                    <FormControl>
                                        <select
                                            className={cn(
                                                buttonVariants({variant: "outline"}),
                                                "w-[200px] appearance-none font-normal"
                                            )} {...field}
                                        >
                                            <option value="pl">Polski</option>
                                            <option value="en-US">English</option>
                                        </select>
                                    </FormControl>
                                </div>
                                {/*<FormDescription> Description </FormDescription>*/}
                                <FormMessage/>
                            </FormItem>
                        )}
                    />
                    <Button className="mt-5" type="submit">Update</Button>
                </form>
            </Form>
            <Toaster />
        </>
    );
}

export default UserDataPage;