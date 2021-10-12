import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import quizReducer from '../Slicer/QuizSlice';
import newCardReducer from '../Slicer/NewCardSlice'

export const store = configureStore({
  reducer: {
    quiz: quizReducer,
    newCard: newCardReducer,
  },
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
