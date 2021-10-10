export interface IQuestionCard {
    question: string,
    answers: string[],
    correctAnswers: number[],
}

export interface IError {
    status: number,
    message: string,
    error: boolean,
}

export interface IQuizState extends IError {
    allCards:IQuestionCard[],
    answeredCards: IQuestionCard[],
}
