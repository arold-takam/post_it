import { http, HttpResponse } from "msw";

export const handlers = [
    // Le "*" au début dit : "Peu importe ce qu'il y a avant, si ça finit par ça, intercepte"
    http.get("*/question/get/all", () => {
        console.log("MOCK INTERCEPTÉ !"); // Ajoute ce log pour voir si ça passe
        return HttpResponse.json([
            { id: 1, content: "Question mocked" },
            { id: 2, content: "Question 2" }
        ]);
    }),
];