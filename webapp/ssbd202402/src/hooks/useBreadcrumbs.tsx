import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import React from "react";
import { NavLink } from "react-router-dom";

export const useBreadcrumbs = (
  breadcrumbs: Array<{ title: string; path: string }>
): React.ReactElement => {
  return (
    <Breadcrumb>
      <BreadcrumbList>
        {breadcrumbs.map((breadcrumb, index) => (
          <React.Fragment key={index}>
            <BreadcrumbItem>
              <BreadcrumbLink asChild>
                <NavLink to={breadcrumb.path}>{breadcrumb.title}</NavLink>
              </BreadcrumbLink>
            </BreadcrumbItem>
            {index === breadcrumbs.length - 1 ? null : <BreadcrumbSeparator />}
          </React.Fragment>
        ))}
      </BreadcrumbList>
    </Breadcrumb>
  );
};
