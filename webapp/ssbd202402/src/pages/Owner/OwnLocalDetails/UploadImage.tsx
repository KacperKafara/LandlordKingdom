import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { ChangeEvent, FC, useEffect, useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useUploadImage } from "@/data/local/useImage";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";
import useAxiosPrivate from "@/data/useAxiosPrivate";
import { ImageComponent } from "./ImageComponent";

type UploadImageCardProps = {
  id: string;
  images: string[];
};

interface Images {
  id: string;
  src: string;
}

const UploadImageCard: FC<UploadImageCardProps> = ({ id, images }) => {
  const [file, setFile] = useState<File | null>(null);
  const { mutate } = useUploadImage();
  const [loadedImages, setLoadedImages] = useState<Images[]>([]);
  const { api } = useAxiosPrivate();

  useEffect(() => {
    const fetchImages = async () => {
      for (const image of images) {
        const response = await api.get(`/images/${image}`);
        setLoadedImages((prev) => [
          ...prev,
          {
            id: image,
            src: `data:${response.headers["content-type"]};base64,${response.data}`,
          },
        ]);
      }
    };

    fetchImages();
  }, [images, api]);

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files != null) {
      const file = event.target.files[0];
      if (file.size >= 256 * 1024) {
        setFile(null);
        toast({
          variant: "destructive",
          description: t("uploadImage.uploadedFileTooLarge"),
        });
        return;
      }
      if (file.type !== "image/png" && file.type !== "image/jpeg") {
        setFile(null);
        toast({
          variant: "destructive",
          description: t("uploadImage.uploadedFileNotImage"),
        });
        return;
      }
      setFile(event.target.files[0]);
    }
  };

  const handleUpload = () => {
    const formData = new FormData();
    setFile(null);
    if (file != null) {
      formData.append("file", file);
      mutate({ id, image: formData });
    }
  };

  return (
    <>
      <Card className="flex flex-col">
        <CardHeader>
          <CardTitle className="text-center">
            {t("uploadImage.uploadImage")}
          </CardTitle>
          <CardDescription className="text-center">
            {t("uploadImage.uploadImageDescription")}
          </CardDescription>
        </CardHeader>
        <CardContent className="flex w-full flex-col items-center justify-center gap-2">
          <Input
            id="picture"
            type="file"
            className="w-1/3 hover:cursor-pointer"
            accept="image/png, image/jpeg"
            onChange={handleChange}
          />
          <Button
            className="mt-2 w-1/3"
            type="submit"
            onClick={() => handleUpload()}
          >
            {t("uploadImage.upload")}
          </Button>
          <div className="flex flex-wrap gap-1">
            {loadedImages.map((image) => (
              <ImageComponent key={image.id} id={image.id} src={image.src} />
            ))}
          </div>
        </CardContent>
      </Card>
    </>
  );
};

export default UploadImageCard;
