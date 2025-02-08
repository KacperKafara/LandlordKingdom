import { Card, CardContent } from "@/components/ui/card";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel";
import { useGetLocalImages } from "@/data/local/useImage";
import { FC, useState } from "react";
import LocalImageDialog from "./LocalImageDialog";

type LocalImagesProps = {
  id: string;
};

const LocalImages: FC<LocalImagesProps> = ({ id }) => {
  const { data: images } = useGetLocalImages(id);
  const [imageId, setImageId] = useState<string>();

  return (
    <div className="relative p-12">
      <Carousel>
        <CarouselContent>
          {images?.map((image) => (
            <CarouselItem key={image} className="basis-1/2">
              <div className="p-1">
                <Card className="flex aspect-square items-center justify-center p-6">
                  <CardContent>
                    <img
                      src={`${import.meta.env.VITE_BACKEND_URL}/images/${image}`}
                      alt="local"
                      className="aspcet-square max-h-48 cursor-pointer"
                      onClick={() => setImageId(image)}
                    />
                  </CardContent>
                </Card>
              </div>
            </CarouselItem>
          ))}
        </CarouselContent>
        <CarouselPrevious />
        <CarouselNext />
      </Carousel>
      <LocalImageDialog
        id={imageId}
        handleClose={() => {
          setImageId(undefined);
        }}
      />
    </div>
  );
};

export default LocalImages;
