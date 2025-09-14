const gombBekuldes = document.getElementById("bekuldes");
const inputFeladat = document.getElementById("feladat");
const listam = document.getElementById("jegyzetlista");

function jegyzet_listahoz_adas(noteText) {
    let elem = document.createElement("li");

    let span = document.createElement("span");
    span.textContent = noteText;

    let checkbox = document.createElement("input");
    checkbox.type = "checkbox";

 
    checkbox.addEventListener("change", function () {
        span.classList.toggle("done", checkbox.checked);
    });

    
    let deleteBtn = document.createElement("button");
    deleteBtn.classList.add("delete-btn");
    deleteBtn.innerHTML = '<i class="fas fa-trash"></i>'; 

    deleteBtn.addEventListener("click", async function () {
        elem.classList.add("fade-out"); // animáció
        setTimeout(() => elem.remove(), 400);

        try {
            await fetch("/note", {
                method: "DELETE",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify([noteText])
            });
        } catch (error) {
            console.error("Hiba a törlésnél:", error);
        }
    });

    elem.appendChild(checkbox);
    elem.appendChild(span);
    elem.appendChild(deleteBtn); 
    listam.prepend(elem);
}


async function jegyzetmentes() {
    const noteText = document.getElementById("feladat").value.trim();

    if (!noteText) {
        
        return;
    }

    try {
        const response = await fetch("/note", {
            method: "POST",
            headers: { "Content-Type": "text/plain" },
            body: noteText
        });

        const message=await response.text();
        jegyzet_listahoz_adas(noteText);

        const now=Date().toLocaleString();
        console.log(message+" : "+noteText+" . "+now)


        document.getElementById("feladat").value = ""; 
    } catch (error) {
        alert("Hiba történt a mentés során!");
        console.error(error);
    }
}

function jegyzetlekeres() {
    fetch("/note")
        .then(response => response.json())
        .then(item => {
            item.forEach(note => {
                jegyzet_listahoz_adas(note);
            });
        });
}

function jegyzetorles() {
    let torles = document.getElementById("delete");

    torles.addEventListener("click", async function () {
        const items = listam.querySelectorAll("li");
        let torlendojegyzetek = [];

        items.forEach(item => {
            const checkbox = item.querySelector("input[type='checkbox']");
            const span = item.querySelector("span");
            if (checkbox.checked) {
                torlendojegyzetek.push(span.textContent);
                listam.removeChild(item);
            }
        });

        if (torlendojegyzetek.length > 0) {
            try {
                await fetch("/note", {
                    method: "DELETE",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(torlendojegyzetek)
                });
            } catch (error) {
                alert("Hiba");
            }
        }
    });
}




gombBekuldes.addEventListener("click", jegyzetmentes);
inputFeladat.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
        event.preventDefault();
        jegyzetmentes();
    }
});


jegyzetlekeres();
jegyzetorles();
