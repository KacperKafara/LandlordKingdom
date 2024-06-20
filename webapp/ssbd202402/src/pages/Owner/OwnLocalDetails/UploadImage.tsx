import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { ChangeEvent, FC, useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useUploadImage } from "@/data/local/useImage";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";
import ImageDisplay from "./ImageComponent";

type UploadImageCardProps = {
  id: string;
  images: string[];
};

const UploadImageCard: FC<UploadImageCardProps> = ({ id, images }) => {
  const [file, setFile] = useState<File | null>(null);
  const { mutate } = useUploadImage();

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
            {images.map((imageId) => (
              <ImageDisplay key={imageId} id={imageId} />
            ))}
          </div>
        </CardContent>
      </Card>
    </>
  );
};

export default UploadImageCard;
