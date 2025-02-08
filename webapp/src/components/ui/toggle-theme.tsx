import { Moon, Sun } from "lucide-react"

import { Button } from "@/components/ui/button"
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { useTheme } from "@/components/ThemeProvider"
import { useChangeTheme } from "@/data/useChangeTheme"
import {useTranslation} from "react-i18next";

export type Theme = "dark" | "light";

export function ModeToggle() {
    const { setTheme } = useTheme()
    const { ThemeMutation } = useChangeTheme();
    const { mutate } = ThemeMutation();
    const { t } = useTranslation();

    const handleThemeChange = (theme: Theme) => {
        setTheme(theme);
        mutate(theme);
    };

    return (
        <DropdownMenu>
            <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon">
                    <Sun className="h-[1.2rem] w-[1.2rem] rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0" />
                    <Moon className="absolute h-[1.2rem] w-[1.2rem] rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100" />
                    <span className="sr-only">Toggle theme</span>
                </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
                <DropdownMenuItem onClick={() => handleThemeChange("light")}>
                    {t("light")}
                </DropdownMenuItem>
                <DropdownMenuItem onClick={() => handleThemeChange("dark")}>
                    {t("dark")}
                </DropdownMenuItem>
            </DropdownMenuContent>
        </DropdownMenu>
    )
}