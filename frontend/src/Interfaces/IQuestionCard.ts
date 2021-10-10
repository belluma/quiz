export interface IQuestionCard {
    question: string,
    choices: string[],
    answerIndices: number[],
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
