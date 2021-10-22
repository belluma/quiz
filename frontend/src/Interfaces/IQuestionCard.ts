export interface IQuestionCard {
    id:number,
    question: string,
    choices: string[],
    answerIndices: number[],
}

export enum cardMode {
    RESULT = "RESULT",
    QUIZ = "QUIZ",
    ALL = "ALL",
    NEW = 'NEW',
}

interface IError {
    status: number,
    message: string,
    error: boolean,
}

export interface IQuizState extends IError {
    allCards:IQuestionCard[],
    answeredCards: IQuestionCard[],
    pointsCounter: number,
}

export interface IAuthState{
    loggedIn: boolean,
    token: string,
}
export interface Istate {
    quiz:IQuizState,
    login: IAuthState,
    error: IError,
}

export enum createCardStatus {
    QUESTION = "QUESTION",
    ANSWER = "ANSWER",
    SELECT = "SELECT"
}
