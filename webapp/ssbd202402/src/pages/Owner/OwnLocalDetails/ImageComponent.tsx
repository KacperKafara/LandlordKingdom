import { useDeleteImage } from "@/data/local/useImage";
import { t } from "i18next";
import { FC, useState } from "react";

interface ImageComponentProps {
  id: string;
  src: string;
}

export const ImageComponent: FC<ImageComponentProps> = ({ id, src }) => {
  const [isHovered, setIsHovered] = useState(false);
  const { mutate: deleteImage } = useDeleteImage();

  const handleDelete = (id: string) => {
    deleteImage(id);
  };

  return (
    <div
      className="relative hover:cursor-pointer"
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      onClick={() => handleDelete(id)}
    >
      <img key={id} src={src} className="max-h-96 max-w-xl" alt="local" />

      {isHovered && (
        <div className="absolute left-0 top-0 flex h-full w-full items-center justify-center bg-black bg-opacity-50">
          <p className="text-center text-2xl text-white">
            {t("uploadImage.delete")}
          </p>
        </div>
      )}
    </div>
  );
};
