
const apiUrl = "/api/assignments";

// Loads assignments from the backend.
async function loadAssignments() {
    const list = document.getElementById("assignmentList");
    const message = document.getElementById("message");

    try {
        const response = await fetch(apiUrl);

        if (!response.ok) {
            throw new Error("Could not load assignments.");
        }

        const assignments = await response.json();
        list.replaceChildren();

        if (assignments.length === 0) {
            message.textContent = "No assignments yet.";
            return;
        }

        message.textContent = "";

        // Create a card for each assignment.
        assignments.forEach(function(assignment) {
            const card = document.createElement("div");
            card.className = "assignment";

            const title = document.createElement("h3");
            title.textContent = assignment.title;

            const course = document.createElement("p");
            course.textContent = "Course: " + assignment.course;

            const deadline = document.createElement("p");
            deadline.textContent = "Deadline: " + assignment.deadline;

            const status = document.createElement("p");
            status.textContent = "Status: " + assignment.status;

            const description = document.createElement("p");
            description.textContent = assignment.description || "";

            card.append(title, course, deadline, status, description);
            list.appendChild(card);
        });

    } catch (error) {
        message.textContent = "Error loading assignments.";
        console.error(error);
    }
}

// Sends a new assignment to the backend.
document.getElementById("assignmentForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const form = event.target;

    const assignment = {
        title: form.querySelector("#title").value,
        course: form.querySelector("#course").value,
        deadline: form.querySelector("#deadline").value,
        description: form.querySelector("#description").value,
        status: "Not Started"
    };

    try {
        const response = await fetch(apiUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(assignment)
        });

        if (!response.ok) {
            throw new Error("Could not save assignment.");
        }

        // Clear the form and refresh the assignment list.
        form.reset();
        await loadAssignments();

        alert("Assignment added successfully!");

    } catch (error) {
        alert("Error adding assignment.");
        console.error(error);
    }
});

// Load assignments when the page opens.
loadAssignments();
