import * as React from "react";

import { cn } from "@/lib/utils";

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  rightText?: string;
}

const InputWithText = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, rightText, ...props }, ref) => {
    return (
      <div className="flex flex-row items-center">
        <input
          type={type}
          className={cn(
            "flex h-10 w-full rounded-l-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50",
            className
          )}
          autoComplete="off"
          ref={ref}
          {...props}
        />
        <span className="h-10 rounded-r-md border border-l-0 border-input px-3 py-2 text-sm">
          {rightText}
        </span>
      </div>
    );
  }
);
InputWithText.displayName = "Input";

export { InputWithText };
