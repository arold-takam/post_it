
export function validateQuestion(content) {
    const errors = [];

    if (!content || content.trim().length < 10) {
        errors.push('Question must be valid(10 characters at least.');
    }

    return errors;
}