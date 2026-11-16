
const studentId = localStorage.getItem("studentId");
const historyContainer = document.getElementById("quizHistory");

function loadQuizHistory() {
    if (!studentId) {
        historyContainer.innerHTML = "<p>Please login first.</p>";
        return;
    }

    fetch(`http://localhost:8081/quiz-results/student/${studentId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Could not load history");
            }
            return response.json();
        })
        .then(results => {
            historyContainer.innerHTML = "";

            if (results.length === 0) {
                historyContainer.innerHTML = "<p>No quiz attempts found.</p>";
                return;
            }

            const clearButton = document.createElement("button");
            clearButton.textContent = "Clear All History";
            clearButton.onclick = deleteAllHistory;
            historyContainer.appendChild(clearButton);

            results.forEach((result, index) => {
                const resultCard = document.createElement("div");

                resultCard.innerHTML = `
                    <h3>Attempt ${index + 1}</h3>
                    <p>Course ID: ${result.courseId}</p>
                    <p>Score: ${result.score} / ${result.totalQuestions}</p>
                `;

                historyContainer.appendChild(resultCard);
            });
        })
        .catch(error => {
            historyContainer.innerHTML =
                "<p>Could not load quiz history.</p>";
            console.error(error);
        });
}

function deleteAllHistory() {
    if (!confirm("Are you sure you want to delete ALL quiz history?")) {
        return;
    }

    fetch(`http://localhost:8081/quiz-results/student/${studentId}`, {
        method: "DELETE"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Delete failed");
            }
            alert("All history deleted successfully!");
            loadQuizHistory();
        })
        .catch(error => {
            alert("Could not delete quiz history.");
            console.error(error);
        });
}

loadQuizHistory();
