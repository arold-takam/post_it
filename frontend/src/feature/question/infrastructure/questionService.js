import apiClient from "./apiClient.js";

// Dans ton UseCase ou ton service
export async function get(){
    try {
        const response = await apiClient.get("/question/get/all");
        return response.data || []; // Sécurité
    } catch (error) {
        console.error("Erreur API", error);
        return []; // Renvoie un tableau vide pour éviter que le .filter() ne crash le front
    }
}


/**
* @param {string|number} id
* @returns {Promise}
*/
export async function getById(id){
    return apiClient.get(`/question/get/id/${id}`);
}


/**
 * @param {Object} questionData
 */
export async function create(questionData){
    return apiClient.post("/question/create", questionData);
}


/**
 *  @param {number|string} id
 *  @param {Object} updateData
 */
export async function update(id, updateData){
    return apiClient.put(`/question/update/${id}`, updateData);
}


/**
 *  @param {number|string} id
 */
export async function answer(id){
    return apiClient.put(`/question/answer/${id}`, {});
}


/**
 * @param {number|string} id
 * @returns {Promise}
 */
export async function deleteQuestion(id){
    return apiClient.delete(`/question/delete/${id}`);
}