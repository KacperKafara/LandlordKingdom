import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { cn } from "@/lib/utils";
import { t } from "i18next";
import { FC } from "react";
import {
  FaAngleDoubleLeft,
  FaAngleDoubleRight,
  FaAngleLeft,
  FaAngleRight,
} from "react-icons/fa";
import { RiExpandUpDownLine } from "react-icons/ri";

interface PageChangerProps {
  pageSize: number;
  totalPages: number;
  pageNumber: number;
  setPageNumber: (pageNumber: number) => void;
  setNumberOfElements: (numberOfElements: number) => void;
  className?: string;
}

export const PageChangerComponent: FC<PageChangerProps> = ({
  pageSize,
  totalPages,
  pageNumber,
  setPageNumber,
  setNumberOfElements,
  className,
}) => {
  return (
    <div className={cn("flex items-center", className)}>
      <div className="flex items-center gap-1">
        <p className="mr-1">{t("pageChanger.numberOfElements")}</p>
        <DropdownMenu>
          <DropdownMenuTrigger>
            <Button
              className="flex h-8 items-center px-2"
              variant="outline"
              role="combobox"
            >
              {pageSize}
              <RiExpandUpDownLine className="ml-3 text-sm" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent>
            <DropdownMenuItem
              onSelect={() => setNumberOfElements(4)}
              className="h-8 px-2"
            >
              4
            </DropdownMenuItem>
            <DropdownMenuItem
              onSelect={() => setNumberOfElements(6)}
              className="h-8 px-2"
            >
              6
            </DropdownMenuItem>
            <DropdownMenuItem
              onSelect={() => setNumberOfElements(8)}
              className="h-8 px-2"
            >
              8
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
      <p>
        {" "}
        {t("pageChanger.page")} {pageNumber + 1} {t("pageChanger.of")}{" "}
        {totalPages}
      </p>
      <div className="flex gap-1">
        <Button
          className="h-8 px-2"
          onClick={() => setPageNumber(0)}
          variant="outline"
          disabled={pageNumber === 0}
        >
          <FaAngleDoubleLeft />
        </Button>
        <Button
          className="h-8 px-2"
          onClick={() => setPageNumber(pageNumber - 1)}
          variant="outline"
          disabled={pageNumber === 0}
        >
          <FaAngleLeft />
        </Button>
        <Button
          className="h-8 px-2"
          onClick={() => setPageNumber(pageNumber + 1)}
          variant="outline"
          disabled={pageNumber === totalPages - 1}
        >
          <FaAngleRight />
        </Button>
        <Button
          className="h-8 px-2"
          onClick={() => setPageNumber(totalPages - 1)}
          variant="outline"
          disabled={pageNumber === totalPages - 1}
        >
          <FaAngleDoubleRight />
        </Button>
      </div>
    </div>
  );
};
