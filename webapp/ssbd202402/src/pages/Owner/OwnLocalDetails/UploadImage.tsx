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
import { useGetLocalImages, useUploadImage } from "@/data/local/useImage";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";

type UploadImageCardProps = {
  id: string;
};

const UploadImageCard: FC<UploadImageCardProps> = ({ id }) => {
  const [file, setFile] = useState<File | null>(null);
  const { mutate } = useUploadImage();
  const { data } = useGetLocalImages(id);

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files != null) {
      const file = event.target.files[0];
      if (file.size >= 256 * 1024) {
        toast({
          variant: "destructive",
          description: "Uploaded file is too large. Max size is 256KB",
        });
        return;
      }
      if (file.type !== "image/png" && file.type !== "image/jpeg") {
        toast({
          variant: "destructive",
          description: "Only .png and .jpeg files are allowed",
        });
        return;
      }
      setFile(event.target.files[0]);
    }
  };

  const handleUpload = () => {
    const formData = new FormData();
    if (file != null) {
      formData.append("file", file);
      mutate({ id, image: formData });
    }
  };

  return (
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
        <div>
          {/* {data && <img src={`data:image;base64,` + data} alt="local" />} */}
        </div>
      </CardContent>
    </Card>
  );
};

export default UploadImageCard;
