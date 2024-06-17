import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ChangeEvent, FC, useState } from "react";
// import { useTranslation } from "react-i18next";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
// import { useLanguageStore } from "@/i18n/languageStore";
import { Button } from "@/components/ui/button";
import { useGetLocalImages, useUploadImage } from "@/data/local/useImage";
import { toast } from "@/components/ui/use-toast";

type UploadImageCardProps = {
  id: string;
};

const UploadImageCard: FC<UploadImageCardProps> = ({ id }) => {
  //   const { t } = useTranslation();
  //   const { language } = useLanguageStore();
  const [file, setFile] = useState<File | null>(null);
  const { mutate } = useUploadImage();
  const { data, isLoading } = useGetLocalImages(
    "ba63f424-ad8e-4d31-9347-c8f9931c57a5"
  );
  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files != null) {
      const file = event.target.files[0];
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
    <Card>
      <CardHeader>
        <CardTitle className="text-center">Upload Image</CardTitle>
      </CardHeader>
      <CardContent className="flex w-full flex-col justify-center">
        <div className="flex w-4/5 flex-col">
          <p className="text-lg font-semibold">
            Upload an image of your local to attract more customers.
          </p>
        </div>
        <div className="grid w-full max-w-sm items-center gap-1.5" lang="en-US">
          <Label htmlFor="picture">Choose file</Label>
          <Input
            id="picture"
            type="file"
            accept="image/png, image/jpeg"
            onChange={handleChange}
          />
        </div>
        <Button
          className="mt-2 w-1/2"
          type="submit"
          onClick={() => handleUpload()}
        >
          Upload
        </Button>
        <div>
          {isLoading && <p>loading...</p>}
          {data && <img src={`data:image;base64,` + data} alt="local" />}
        </div>
      </CardContent>
    </Card>
  );
};

export default UploadImageCard;
