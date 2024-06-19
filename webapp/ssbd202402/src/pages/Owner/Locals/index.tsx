import RefreshQueryButton from "@/components/RefreshQueryButton";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useGetOwnLocals } from "@/data/mol/useGetOwnLocals";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { t } from "i18next";
import { FC, useState } from "react";
import { LoadingData } from "@/components/LoadingData";
import { NavLink, useNavigate } from "react-router-dom";
import { PageChangerComponent } from "@/pages/Components/PageChangerComponent";
import { getAddressString } from "@/utils/address";
import {
  DropdownMenu,
  DropdownMenuItem,
  DropdownMenuTrigger,
  DropdownMenuContent,
} from "@/components/ui/dropdown-menu";
import { RiExpandUpDownLine } from "react-icons/ri";

interface LocalStateDropdown {
  shownValue: string;
  sendedValue: string;
}

const Locals: FC = () => {
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(6);
  const [localState, setLocalState] = useState<LocalStateDropdown>({
    shownValue: t("ownerLocals.all"),
    sendedValue: "ALL",
  });
  const { data: localsPage, isLoading } = useGetOwnLocals({
    pageNumber: pageNumber,
    pageSize: pageSize,
    state: localState.sendedValue,
  });
  const locals = localsPage?.locals;

  const breadCrumbs = useBreadcrumbs([
    { title: t("ownerLocals.title"), path: "/owner" },
    { title: t("ownerLocals.locals"), path: "/owner/locals" },
  ]);

  const navigate = useNavigate();

  if (!localsPage) {
    return <LoadingData />;
  }

  if (isLoading) {
    return <LoadingData />;
  }

  if (localState.sendedValue === "ALL" && (!locals || locals.length === 0)) {
    return (
      <div className="flex h-full justify-center">
        <div className="w-full pt-2">
          {breadCrumbs}
          <div className="mt-5 flex flex-col items-center">
            <p className="text-xl">{t("ownerLocals.noLocalsFound")}</p>
            <div className="flex gap-3">
              <Button
                className="mt-3 w-fit"
                onClick={() => navigate("/owner/addLocalForm")}
              >
                {t("ownerLocals.addFirstLocal")}
              </Button>
              <RefreshQueryButton className="mt-3" queryKeys={["ownLocals"]} />
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="flex h-full flex-col">
      <div className="flex flex-row items-center justify-between">
        {breadCrumbs}
        <div className="flex flex-row gap-4">
          <Button asChild className="self-end">
            <NavLink to="report">Report</NavLink>
          </Button>
          <RefreshQueryButton queryKeys={["ownLocals"]} />
        </div>
      </div>
      <div className="flex h-full max-w-[1920px] flex-col">
        <ul className="flex flex-wrap justify-center gap-2 py-4">
          {locals?.length === 0 && (
            <div className="flex flex-col">
              <p className="text-2xl">
                {t("ownerLocals.noLocalsFoundForThisState")} 🤷‍♀️
              </p>
            </div>
          )}
          {locals?.length != 0 &&
            locals?.map((local) => (
              <li
                key={local.id}
                className="min-w-[35rem] max-w-[35rem] flex-1 "
              >
                <Card className="relative">
                  <Button
                    onClick={() => navigate(`local/${local.id}`)}
                    className="absolute right-1 top-1"
                    variant="ghost"
                  >
                    {t("ownerLocals.show")}
                  </Button>
                  <CardHeader>
                    <CardTitle>{local.name}</CardTitle>
                    <CardDescription>
                      {getAddressString(local.address)}
                    </CardDescription>
                  </CardHeader>
                  <CardContent className="flex-grow">
                    {local.description}
                  </CardContent>
                  <CardFooter>{t(`localState.${local.state}`)}</CardFooter>
                </Card>
              </li>
            ))}
        </ul>
        <PageChangerComponent
          totalPages={localsPage.totalPages}
          pageNumber={pageNumber}
          pageSize={pageSize}
          setPageNumber={setPageNumber}
          setNumberOfElements={setPageSize}
          className="mb-3 flex justify-between"
        >
          <div className="flex items-center gap-2">
            <p>{t("ownerLocals.localState")}</p>
            <DropdownMenu>
              <DropdownMenuTrigger>
                <Button
                  className="flex h-8 items-center px-2"
                  variant="outline"
                  role="combobox"
                >
                  {localState.shownValue}
                  <RiExpandUpDownLine className="ml-3 text-sm" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent>
                <DropdownMenuItem
                  onSelect={() =>
                    setLocalState({
                      shownValue: t("ownerLocals.all"),
                      sendedValue: "ALL",
                    })
                  }
                  className="h-8 px-2"
                >
                  {t("ownerLocals.all")}
                </DropdownMenuItem>
                <DropdownMenuItem
                  onSelect={() =>
                    setLocalState({
                      shownValue: t("localState.ACTIVE"),
                      sendedValue: "ACTIVE",
                    })
                  }
                  className="h-8 px-2"
                >
                  {t("localState.ACTIVE")}
                </DropdownMenuItem>
                <DropdownMenuItem
                  onSelect={() =>
                    setLocalState({
                      shownValue: t("localState.INACTIVE"),
                      sendedValue: "INACTIVE",
                    })
                  }
                  className="h-8 px-2"
                >
                  {t("localState.INACTIVE")}
                </DropdownMenuItem>
                <DropdownMenuItem
                  onSelect={() =>
                    setLocalState({
                      shownValue: t("localState.RENTED"),
                      sendedValue: "RENTED",
                    })
                  }
                  className="h-8 px-2"
                >
                  {t("localState.RENTED")}
                </DropdownMenuItem>
                <DropdownMenuItem
                  onSelect={() =>
                    setLocalState({
                      shownValue: t("localState.UNAPPROVED"),
                      sendedValue: "UNAPPROVED",
                    })
                  }
                  className="h-8 px-2"
                >
                  {t("localState.UNAPPROVED")}
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </div>
        </PageChangerComponent>
      </div>
    </div>
  );
};

export default Locals;
