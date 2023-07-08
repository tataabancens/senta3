import ReservationModel from "./ReservationModel";

export default interface ReservationsPage {
    reservations: ReservationModel[];
    page: number;
    last: number;
    prev: number;
    next: number;
    first: number;
    status: number;
}

interface LinkHeader {
    [key: string]: number;
}


// Función para analizar el encabezado Link y extraer los enlaces
export const parseLinkHeader = (header: string) => {
    const linkRegex = /<([^>]+)>;\s*rel="([^"]+)"/g;
    const links: LinkHeader = {};

    let match;
    while ((match = linkRegex.exec(header)) !== null) {
        const url = match[1];
        const rel = match[2];

        const stringValue = extractQueryParam(url, "page");
        links[rel] = parseInt(stringValue);
    }

    return links;
}

const extractQueryParam = (urlString: string, paramName: string): string => {
    const url = new URL(urlString);
    const params = new URLSearchParams(url.search);
    return params.get(paramName) || "0";
  }