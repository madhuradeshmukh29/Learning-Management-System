const params = new URLSearchParams(window.location.search);
const selectedCourseId = params.get("id");

const courseList = document.getElementById("courseList");


async function loadCourses() {
    try {
        const response = await fetch("/courses");

        if (!response.ok) {
            throw new Error("Could not load courses");
        }

        const courses = await response.json();

        courseList.innerHTML = "";

        if (courses.length === 0) {
            courseList.textContent = "No courses available yet.";
            return;
        }

        const lessonResponse = await fetch("/lessons");

        if (!lessonResponse.ok) {
            throw new Error("Could not load lessons");
        }

        const lessons = await lessonResponse.json();

        courses
    .filter(function (course) {
        return !selectedCourseId ||
            String(course.id) === selectedCourseId;
    })
    .forEach(function (course) {

            const courseCard = document.createElement("div");

            const title = document.createElement("h2");
            title.textContent = course.title;

            const description = document.createElement("p");
            description.textContent = course.description;

            const enrollButton = document.createElement("button");
enrollButton.textContent = "Enroll in Course";

enrollButton.addEventListener("click", async function () {
    try {
        const response = await fetch("/enrollments", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },

           body: JSON.stringify({
    studentId: Number(localStorage.getItem("studentId")),
    courseId: course.id
})
        });

        if (!response.ok) {
    const message = await response.text();
    alert(message);
    return;
}

alert("Enrolled successfully!");

    } catch (error) {
        alert("Could not enroll. Please try again.");
    }
});

            const lessonHeading = document.createElement("h3");
            lessonHeading.textContent = "Lessons";

            courseCard.appendChild(title);
            courseCard.appendChild(description);
            courseCard.appendChild(lessonHeading);

            const courseLessons = lessons.filter(function (lesson) {
                return lesson.courseId === course.id;
            });

            if (courseLessons.length === 0) {
                const message = document.createElement("p");
                message.textContent = "No lessons available yet.";
                courseCard.appendChild(message);
            } else {
                courseLessons.forEach(function (lesson) {
                    const lessonTitle = document.createElement("h4");
                    lessonTitle.textContent = lesson.title;

                    
 
              lessonTitle.style.cursor = "pointer";

              lessonTitle.addEventListener("click", function () {
              window.location.href = "lesson.html?id=" + lesson.id;
          });



                   

                    courseCard.appendChild(lessonTitle);
                    
                    
                    

                });
            }
            courseCard.appendChild(enrollButton);

            
const quizButton = document.createElement("button");
quizButton.textContent = "Take Quiz";

quizButton.addEventListener("click", function () {
    window.location.href = "quiz.html?courseId=" + course.id;
});

courseCard.appendChild(quizButton);

            courseList.appendChild(courseCard);
        });

    } catch (error) {
        courseList.textContent =
            "Could not load courses. Please check the server.";
    }
}

loadCourses();

