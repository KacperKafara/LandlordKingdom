import {FC} from "react";
import { Button } from "@/components/ui/button"
import {Link} from "react-router-dom";


const MePage : FC = () => {


    return (
        <>
            <div className="flex justify-center mt-5">
                <Button variant="outline">
                    <Link to={"info"}>Account Info</Link>
                </Button>
            </div>
        </>
    );
}

export default MePage;