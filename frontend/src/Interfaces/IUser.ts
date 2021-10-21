export interface IUser{
    username: string,
    email?: string,
    id?: number,
    highscore?:number,
    dateHighscore?:Date,
    memberSince?:Date
    password?:string
}

export interface IHighscore {
    username:string,
    score: number,
    id: number,
    date: Date
}
