import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useGetOwnLocals } from "@/data/mol/useGetOwnLocals";
import { FC } from "react";
import Loader from "react-js-loader";

const Locals: FC = () => {
  const { data: locals, isLoading } = useGetOwnLocals();
  // const locals: OwnLocals[] = [];
  // const isLoading = true;

  return (
    <>
      {isLoading && <Loader type="box-up" />}
      {!isLoading && locals && locals.length === 0 && (
        <div>No locals found</div>
      )}
      {!isLoading && locals && locals.length > 0 && (
        <div className="my-3 grid grid-cols-1 gap-2 md:grid-cols-3">
          {locals.map((local) => (
            <Card key={local.id}>
              <CardHeader>
                <CardTitle>{local.name}</CardTitle>
                <CardDescription>{local.description}</CardDescription>
              </CardHeader>
              <CardContent>
                <p>{local.address.street}</p>
                <p>{local.address.city}</p>
                <p>{local.address.zipCode}</p>
              </CardContent>
              <CardFooter>
                <p>{local.state}</p>
              </CardFooter>
            </Card>
          ))}
        </div>
      )}
    </>
  );
};

export default Locals;
