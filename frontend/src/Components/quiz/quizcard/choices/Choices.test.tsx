import React from 'react';
import ReactDOM from 'react-dom';
import Choices from './Choices';
import {cardMode} from "../../../../Interfaces/IQuestionCard";
import fn = jest.fn;
import {fireEvent, render, screen} from "@testing-library/react";

let container: HTMLElement | null = null;
beforeEach(() => {
    container = document.createElement('div');
    document.body.appendChild(container);
});

afterEach(() => {
    if (container) {
        ReactDOM.unmountComponentAtNode(container);
        container.remove();
    }
    container = null;
})
const choices: string[] = ['a', 'b', 'c', 'd'];
const selected: number[] = [];
describe("test choices in all cardmodes", () => {
    it('renders without crashing in mode NEW', () => {
        const div = document.createElement('div');
        ReactDOM.render(<Choices choices={choices} mode={cardMode.NEW} selectAnswer={fn} selected={selected}/>, div);
    });
    it('renders without crashing in mode QUIZ', () => {
        const div = document.createElement('div');
        ReactDOM.render(<Choices choices={choices} mode={cardMode.QUIZ} selectAnswer={fn} selected={selected}/>, div);
    });
    it('renders without crashing in mode RESULT', () => {
        const div = document.createElement('div');
        ReactDOM.render(<Choices choices={choices} mode={cardMode.RESULT} selectAnswer={fn} selected={selected}/>, div);
    });
    it('renders without crashing in mode ALL', () => {
        const div = document.createElement('div');
        ReactDOM.render(<Choices choices={choices} mode={cardMode.ALL} selectAnswer={fn} selected={selected}/>, div);
    });
})
describe("test choices are present", () => {
    test('mode NEW', () => {
        render(<Choices choices={choices} mode={cardMode.NEW} selectAnswer={fn} selected={selected}/>)
        choices.forEach(choice => expect(screen.getByText(choice)).toBeInTheDocument())
    })
    test('mode RESULT', () => {
        render(<Choices choices={choices} mode={cardMode.RESULT} selectAnswer={fn} selected={selected}/>)
        choices.forEach(choice => expect(screen.getByText(choice)).toBeInTheDocument())
    })
    test('mode QUIZ', () => {
        render(<Choices choices={choices} mode={cardMode.QUIZ} selectAnswer={fn} selected={selected}/>)
        choices.forEach(choice => expect(screen.getByText(choice)).toBeInTheDocument())
    })
    test('mode ALL', () => {
        render(<Choices choices={choices} mode={cardMode.ALL} selectAnswer={fn} selected={selected}/>)
        choices.forEach(choice => expect(screen.getByText(choice)).toBeInTheDocument())
    })
})

describe("test radio buttons work", () => {
    test("test onChange fires when clicking on radio or label", () => {
        const handleChange = jest.fn();
        const component = render(<Choices choices={choices} mode={cardMode.QUIZ} selectAnswer={handleChange}
                                          selected={selected}/>)
        const radios = (screen.getAllByRole('radio'))
        const labels = choices.map((choice) => screen.getByText(choice));
        [...radios, ...labels].forEach((radio, i) => {
            fireEvent.click(radio);
        })
        expect(handleChange).toHaveBeenCalledTimes(8);

    })
    test("onChange doesn't fire when clicking on already selected label or radio", () => {
        const handleChange = jest.fn();
        const component = render(<Choices choices={choices} mode={cardMode.QUIZ} selectAnswer={handleChange}
                                          selected={[0]}/>)
        const radio = screen.getAllByRole('radio')[0];
        const label = screen.getByText('a');
        fireEvent.click(radio);
        fireEvent.click(label);
        expect(handleChange).toHaveBeenCalledTimes(0);
    })
})

