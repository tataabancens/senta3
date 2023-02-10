export default interface ApiErrorDetails {
    status: number,
    title: string,
    message: string,
    path: string,
    errors?: ApiError[],
}

export interface ApiError {
    description: string,
    property: string,
}