import { describe, it, expect, beforeAll, afterEach, afterAll } from 'vitest';
import { renderHook, waitFor } from '@testing-library/react';
import useQuestion from './useQuestion';
import { server } from '../../../mocks/server.js'; // Configuration MSW

describe('useQuestion Hook', () => {
    // On configure le cycle de vie du serveur de mock pour ESLint et Vitest
    beforeAll(() => server.listen());
    afterEach(() => server.resetHandlers());
    afterAll(() => server.close());

    it('doit récupérer la liste des questions et les formater', async () => {
        const { result } = renderHook(() => useQuestion());

        // On attend que le chargement passe à false, c'est un meilleur indicateur
        await waitFor(() => {
            expect(result.current.loading).toBe(false);
        }, { timeout: 2000 });

        console.log("Contenu de la liste après chargement :", result.current.list);

        expect(result.current.list.length).toBeGreaterThan(0);
    });
});