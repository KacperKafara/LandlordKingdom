import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ChangeEvent, FC, useState } from "react";
// import { useTranslation } from "react-i18next";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
// import { useLanguageStore } from "@/i18n/languageStore";
import { Button } from "@/components/ui/button";
import { useGetLocalImages, useUploadImage } from "@/data/local/useImage";

type UploadImageCardProps = {
  id: string;
};

const UploadImageCard: FC<UploadImageCardProps> = ({ id }) => {
  //   const { t } = useTranslation();
  //   const { language } = useLanguageStore();
  const [file, setFile] = useState<File | null>(null);
  const { mutate } = useUploadImage();
  const { data, isLoading } = useGetLocalImages(
    "d5f21389-16a0-4c3e-8710-00d1ed79592a"
  );
  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files != null) {
      setFile(event.target.files[0]);
    }
  };

  const handleUpload = () => {
    if (file != null) {
      const reader = new FileReader();
      reader.onload = async () => {
        const arrayBuffer = await file.arrayBuffer();
        const array = new Int8Array(arrayBuffer);
        mutate({ id, image: array });
      };
      reader.readAsArrayBuffer(file);
    }
  };
  return (
    <Card>
      <CardHeader>
        <CardTitle className="text-center">Upload Image</CardTitle>
      </CardHeader>
      <CardContent className="flex flex-col justify-center">
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
