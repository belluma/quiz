import React from 'react';
import CardCreationDialog from './CardCreationDialog';
import {store} from "../../../../app/store";
import {Provider} from "react-redux";
import {render, screen} from "@testing-library/react";

it('renders without crashing', () => {
    render(<Provider store={store}><CardCreationDialog/></Provider>)
});

describe("CardContent", () => {
    it("renders CustomFormGroup question on initial render", () => {

    })
})


describe("QuizcardHeader", () => {
    test("status gets reset from ANSWER to QUESTION on click", () => {
        const dialog = render(<Provider store={store}><CardCreationDialog/></Provider>)
        const header = screen.getByRole("heading");


        console.log(header);
    })
test("status gets reset from SELECT to QUESTION on click", () => {
        const dialog = render(<Provider store={store}><CardCreationDialog/></Provider>)
        const header = screen.getByRole("heading");

        console.log(header);
    })
})

describe("CardContent", () => {
    test("Question Form Group renders when status == question", () => {
    });
    it('sets question text on input in text field', () => {
    });
    it("advances to answer status on button click", () => {
    });

    test("Choices Form Group renders when status == answer", () => {
    });
    it("sets choice text on input in text field", () => {
    });
    it("saves choice on button click", () => {
    });
    it("resets value on save choice", () => {
    });
    it("renders a choice after choice has been added", () => {
    });
    it("removes answer from choices on delete button", () => {
    })
})

describe("CardFooter", () => {
    it("disables button when less than two choices", () => {
    })
    it("disables button when no correct answer chosen", () => {
    })
    it("disables button when no question text", () => {
    })
    it("executes save card on button click", () => {
    })
})
