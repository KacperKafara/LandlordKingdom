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
    const sortedApplicationsList = applicationsList.sort((a, b) => {
        const dateA = new Date(a.createdAt).getTime();
        const dateB = new Date(b.createdAt).getTime();
        return dateA - dateB;
    });
    return (
        <Card>
            <CardHeader>
                <CardTitle className="text-center">
                    {t("ownLocalDetails.showApplications")}
                </CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
                {sortedApplicationsList.length > 0 ? (
                    sortedApplicationsList.map((application) => (
                        <Card key={application.id} className="p-4">
                            <div className="flex justify-between items-center">
                                <div>
                                    <p>
                                        <strong>{t("localApplications.applicant")}:</strong> {application.applicantFirstName + " " + application.applicantLastName}
                                    </p>
                                    <p>
                                        <strong>{t("localApplications.createdAt")}:</strong> {new Date(application.createdAt).toLocaleString()}
                                    </p>
                                    <p>
                                        <strong>{t("localApplications.email")}:</strong> {application.applicantEmail}
                                    </p>
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
