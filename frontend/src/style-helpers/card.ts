export const makeCardChangeBetweenPortraitAndLandscape = (signup: boolean = false) => (
     {
        height: {
            xs: 500,
            sm: signup ? 500 : 345
        }, width: {
            xs: 345, sm: 500
        }, borderRadius: 10, position: "relative"
    } as const
)

export const portraitView = () => (
    {
        height: 500, width: 345, borderRadius: 10, position: "relative"
    } as const
)



export const styleCardContent =(dialogStatus = "") => {
    const styles = {
        position: "absolute",
        bottom: 50,
        width: "100%",
        bgcolor: 'primary.main',
        color: 'primary.contrastText'
    } as const;
    const {bottom, position, ...qStyles} = {...styles} as const;
    return dialogStatus === "QUESTION" ? qStyles : styles;
}
