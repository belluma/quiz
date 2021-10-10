import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import quizReducer from '../Slicer/QuizSlice';

export const store = configureStore({
  reducer: {
    quiz: quizReducer,
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
