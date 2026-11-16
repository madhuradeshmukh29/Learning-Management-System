const params = new URLSearchParams(window.location.search);
const selectedCourseId = params.get("id");

const myCourses = document.getElementById("myCourses");

const studentId = localStorage.getItem("studentId");

if (!studentId) {
    window.location.href = "login.html";
}

async function loadMyCourses() {
    try {
        const response = await fetch(
            `/enrollments/student/${studentId}`
        );

        if (!response.ok) {
            throw new Error("Could not load enrollments");
        }

        const enrollments = await response.json();
        const courseResponse = await fetch("/courses");

        if (!courseResponse.ok) {
            throw new Error("Could not load courses");
        }

        const courses = await courseResponse.json();
        myCourses.innerHTML = "";

        if (enrollments.length === 0) {
            myCourses.textContent = "You have not enrolled in any courses yet.";
            return;
        }

        enrollments.forEach(function (enrollment) {
            const course = courses.find(function (item) {
                return item.id === enrollment.courseId;
            });

            if (course) {
              const courseTitle = document.createElement("p");

const courseLink = document.createElement("a");
courseLink.textContent = course.title;
courseLink.href = "courses.html?id=" + course.id;

courseTitle.appendChild(courseLink);
myCourses.appendChild(courseTitle);
                
            }
        });
    } catch (error) {
        myCourses.textContent = "Could not load your courses. Please try again.";
    }
}

loadMyCourses();

function logout() {
    localStorage.removeItem("studentId");
    window.location.href = "login.html";
}