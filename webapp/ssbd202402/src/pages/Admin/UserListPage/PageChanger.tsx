import { useUsersFilterStore } from "@/store/usersFilterStore";
import { ChevronLeft, ChevronRight } from "lucide-react";
import { FC } from "react";

const PageChanger: FC = () => {
  const { pageNumber, totalPages, setPageNumber } = useUsersFilterStore();

  const prevPage = () => {
    if (pageNumber > 0) {
      setPageNumber(pageNumber - 1);
    }
  };

  const nextPage = () => {
    if (pageNumber < totalPages - 1) {
      setPageNumber(pageNumber + 1);
    }
  };

  return (
    <>
      <div className="flex gap-1">
        <ChevronLeft
          className={pageNumber > 0 ? "cursor-pointer" : "opacity-25"}
          onClick={() => prevPage()}
        />
        <div className="font-bold">{pageNumber + 1}</div>
        <ChevronRight
          className={
            pageNumber < totalPages - 1 ? "cursor-pointer" : "opacity-25"
          }
          onClick={() => nextPage()}
        />
      </div>
    </>
  );
};

export default PageChanger;
