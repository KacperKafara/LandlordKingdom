import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Form, FormField, FormItem, FormLabel, FormControl } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { t } from "i18next";
import DataField from "@/components/DataField";
import { LoadingData } from "@/components/LoadingData";
import { PageChangerComponent } from "@/pages/Components/PageChangerComponent";
import { useGetActiveLocals } from "@/data/mol/useGetActiveLocals";
import RefreshQueryButton from "@/components/RefreshQueryButton.tsx";
import {useBreadcrumbs} from "@/hooks/useBreadcrumbs.tsx";

interface FilterLocals {
    city: string | null;
    minSize: number | null;
    maxSize: number | null;
}

const ActiveLocalsComponent = () => {
    const [filters, setFilters] = useState<FilterLocals>({
        city: null,
        minSize: null,
        maxSize: null,
    });
    const [pageNumber, setPageNumber] = useState(0);
    const [pageSize, setPageSize] = useState(6);
    const navigate = useNavigate();
    const filterData = useForm<FilterLocals>({
        defaultValues: {
            city: null,
            minSize: null,
            maxSize: null,
        },
    });
    const { data: localsPage, isLoading, refetch } = useGetActiveLocals({
        pageNumber: pageNumber,
        pageSize: pageSize,
        city: filters.city,
        minSize: filters.minSize,
        maxSize: filters.maxSize,
    });

    useEffect(() => {
        refetch();
    }, [pageNumber, pageSize, filters, refetch]);

    const locals = localsPage?.locals;
    const handleFilterSubmit = filterData.handleSubmit((data) => {
        setFilters(data);
        setPageNumber(0);
    });

    const breadcrumbs = useBreadcrumbs([
        { title: t("breadcrumbs.tenant"), path: "/tenant" },
        { title: t("breadcrumbs.locals"), path: "/tenant/locals" },
    ]);

    const handleClearFilters = () => {
        const defaultValues = {
            city: null,
            minSize: null,
            maxSize: null,
        };
        filterData.reset(defaultValues);
        setFilters(defaultValues);
        setPageNumber(0);
    };

    return (
        <div className="justify-center">
            <div className="flex flex-row items-center justify-between">
                {breadcrumbs}
                <RefreshQueryButton queryKeys={["tenantOwnRents"]}/>
            </div>
            <Form {...filterData}>
                <form onSubmit={handleFilterSubmit}>
                    <div className="flex flex-wrap justify-center gap-3">
                        <FormField
                            control={filterData.control}
                            name="city"
                            render={({field}) => (
                                <FormItem>
                                    <FormLabel>{t("localFilter.city")}</FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type="text"
                                            placeholder={t("localFilter.cityPlaceholder") || ""}
                                            value={field.value ?? ""}
                                            onChange={(e) => field.onChange(e.target.value || null)}
                                        />
                                    </FormControl>
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={filterData.control}
                            name="minSize"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>{t("localFilter.minSize")}</FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type="number"
                                            placeholder={t("localFilter.minSizePlaceholder") || ""}
                                            value={field.value === null ? "" : field.value}
                                            onChange={(e) => {
                                                const value = e.target.value === "" ? null : Number(e.target.value);
                                                if (value === null || value >= 0) {
                                                    field.onChange(value);
                                                }
                                            }}
                                        />
                                    </FormControl>
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={filterData.control}
                            name="maxSize"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>{t("localFilter.maxSize")}</FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type="number"
                                            placeholder={t("localFilter.maxSizePlaceholder") || ""}
                                            value={field.value === null ? "" : field.value}
                                            onChange={(e) => {
                                                const value = e.target.value === "" ? null : Number(e.target.value);
                                                if (value === null || value >= 1) {
                                                    field.onChange(value);
                                                }
                                            }}
                                        />
                                    </FormControl>
                                </FormItem>
                            )}
                        />
                        <div className="flex items-end gap-3">
                            <Button type="submit">{t("localFilter.submit")}</Button>
                            <Button
                                type="button"
                                variant="ghost"
                                className="flex items-center gap-2"
                                onClick={handleClearFilters}
                            >
                                {t("localFilter.clear")}
                            </Button>
                        </div>
                    </div>
                </form>
            </Form>

            {isLoading && <LoadingData/>}

            {!isLoading && (!locals || locals.length === 0) && (
                <div className="text-center text-gray-500 mb-5 my-5">
                    {t("activeLocals.noLocalsFound")}
                </div>
            )}

            {!isLoading && locals && locals.length > 0 && (
                <div>
                    <div className="flex h-full justify-center">
                        <div className="my-3 grid w-11/12 grid-cols-1 gap-2 md:grid-cols-2">
                            {locals.map((local) => (
                                <Card className="relative" key={local.id}>
                                    <Button
                                        className="absolute right-1 top-1"
                                        variant="ghost"
                                        onClick={() => navigate(`/tenant/locals/${local.id}`)}
                                    >
                                        {t("activeLocals.show")}
                                    </Button>
                                    <CardHeader>
                                        <CardTitle>{local.name}</CardTitle>
                                        <CardDescription>{local.description.substring(0, 80) + "..."}</CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <div className="grid grid-cols-2">
                                            <DataField label={t("activeLocals.city")} value={local.city}/>
                                            <DataField label={t("activeLocals.size")} value={local.size + " m²"}/>
                                        </div>
                                    </CardContent>
                                </Card>
                            ))}
                        </div>
                    </div>
                    <PageChangerComponent
                        totalPages={localsPage.pages}
                        pageNumber={pageNumber}
                        pageSize={pageSize}
                        setPageNumber={setPageNumber}
                        setNumberOfElements={setPageSize}
                        className="mb-3 flex justify-between"
                    />
                </div>
            )}
        </div>
    );
};

export default ActiveLocalsComponent;
