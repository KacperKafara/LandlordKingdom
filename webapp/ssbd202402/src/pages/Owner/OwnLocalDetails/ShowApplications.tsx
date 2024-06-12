import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import {useGetLocalApplications} from "@/data/local/useGetLocalApplications.ts";
import {useParams} from "react-router-dom";
import { LocalApplications as ApplicationType } from "@/types/local/LocalApplications";

const LocalApplications: FC = () => {
    const { id } = useParams<{ id: string }>();
    const { t } = useTranslation();
    const { applications   } = useGetLocalApplications(id!);
    const applicationsList: ApplicationType[] = Array.isArray(applications) ? applications : [];
    return (
        <Card>
            <CardHeader>
                <CardTitle className="text-center">
                    {t("ownLocalDetails.showApplications")}
                </CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
                {applicationsList.length > 0 ? (
                    applicationsList.map((application) => (
                        <Card key={application.id} className="p-4">
                            <div className="flex justify-between items-center">
                                <div>
                                    <p><strong>{t("localApplications.applicantLogin")}:</strong> {application.applicantLogin}</p>
                                    <p><strong>{t("localApplications.createdAt")}:</strong> {new Date(application.createdAt).toLocaleString()}</p>
                                </div>
                                <div className="flex space-x-2">
                                    <Button variant="default">
                                        {t("localApplications.accept")}
                                    </Button>
                                    <Button variant="destructive">
                                        {t("localApplications.reject")}
                                    </Button>
                                </div>
                            </div>
                        </Card>
                    ))
                ) : (
                    <p>{t("localApplications.noApplications")}</p>
                )}
            </CardContent>
        </Card>
    );
};

export default LocalApplications;
