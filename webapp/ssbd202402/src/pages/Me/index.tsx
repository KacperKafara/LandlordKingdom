import {FC} from "react";
import { Button } from "@/components/ui/button"
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";


const MePage : FC = () => {
    const {t} = useTranslation();

    return (
        <>
            <div className="flex justify-center mt-5">
                <Button variant="outline">
                    <Link to={"info"}>{t("mePage.accountInfo")} </Link>
                </Button>
            </div>
        </>
    );
}

export default MePage;