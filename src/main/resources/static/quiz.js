
const params = new URLSearchParams(window.location.search);
const courseId = params.get("courseId");

const quizQuestions = document.getElementById("quizQuestions");
const submitButton = document.getElementById("submitQuiz");
const quizResult = document.getElementById("quizResult");

let questions = [];

async function loadQuiz() {
    try {
        if (!courseId) {
            quizQuestions.textContent = "Course ID is missing.";
            submitButton.disabled = true;
            return;
        }

        const response = await fetch(
            `/quiz-questions?courseId=${courseId}`
        );

        if (!response.ok) {
            throw new Error("Could not load quiz");
        }

        questions = await response.json();
        quizQuestions.innerHTML = "";

        if (questions.length === 0) {
            quizQuestions.textContent = "No quiz questions available.";
            submitButton.disabled = true;
            return;
        }

        questions.forEach(function (question, index) {
            const section = document.createElement("div");
            const heading = document.createElement("h3");

            heading.textContent = (index + 1) + ". " + question.question;
            section.appendChild(heading);

            ["A", "B", "C", "D"].forEach(function (letter) {
                const label = document.createElement("label");
                const radio = document.createElement("input");

                radio.type = "radio";
                radio.name = "question-" + question.id;
                radio.value = letter;

                label.appendChild(radio);
                label.appendChild(
                    document.createTextNode(" " + question["option" + letter])
                );

                section.appendChild(label);
                section.appendChild(document.createElement("br"));
            });

            quizQuestions.appendChild(section);
        });

    } catch (error) {
        quizQuestions.textContent =
            "Could not load quiz. Please check the server.";
        console.error(error);
    }
}

submitButton.addEventListener("click", async function () {
    const answers = {};

    
if (questions.some(function (question) {
    return !document.querySelector(
        'input[name="question-' + question.id + '"]:checked'
    );
})) {
    quizResult.textContent = "Please answer all questions before submitting.";
    return;
}


    questions.forEach(function (question) {
        const selected = document.querySelector(
            'input[name="question-' + question.id + '"]:checked'
        );

        if (selected) {
            answers[question.id] = selected.value;
        }
    });

    try {
        submitButton.disabled = true;
        quizResult.textContent = "Checking your answers...";

        const response = await fetch("/quiz-questions/submit", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },

           body: JSON.stringify({
           studentId: Number(localStorage.getItem("studentId")),
           courseId: Number(courseId),
           answers: answers
})
        });

        if (!response.ok) {
            throw new Error("Quiz submission failed");
        }

        const result = await response.json();

        quizResult.textContent =
            "Your Score: " + result.score + " / " + result.totalQuestions;

    } catch (error) {
        quizResult.textContent =
            "Could not submit quiz. Please try again.";
        console.error(error);
    } finally {
        submitButton.disabled = false;
    }
});

loadQuiz();

document.getElementById("backToCourse").addEventListener("click", function () {
    window.location.href = "courses.html?id=" + courseId;
});
