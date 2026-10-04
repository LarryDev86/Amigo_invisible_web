/*
 * ==============================
 * DECORACIÓN NAVIDEÑA
 * ==============================
 */

const decoracion = document.getElementById("decoracion-navidad");

const elementos = [
    "❄",
    "❄",
    "❄",
    "❄",
    "✦",
    "✧",
    "🎁"
];

function crearElementoNavidad() {

    if (!decoracion) {
        return;
    }

    const elemento = document.createElement("span");

    const contenido =
        elementos[Math.floor(Math.random() * elementos.length)];

    elemento.textContent = contenido;

    elemento.classList.add("elemento-navidad");


    // Posición horizontal aleatoria
    elemento.style.left =
        Math.random() * 100 + "%";


    // Tamaños distintos
    const tamano =
        contenido === "🎁"
            ? 25 + Math.random() * 30
            : 15 + Math.random() * 20;

    elemento.style.fontSize =
        tamano + "px";


    // Velocidad diferente
    const duracion =
        8 + Math.random() * 10;

    elemento.style.animationDuration =
        duracion + "s";


    // Movimiento horizontal
    elemento.style.setProperty(
        "--movimiento-x",
        (-60 + Math.random() * 120) + "px"
    );


    decoracion.appendChild(elemento);


    // Eliminamos el elemento cuando termina
    setTimeout(() => {

        elemento.remove();

    }, duracion * 1000);
}


/*
 * Creamos elementos continuamente
 */

if (decoracion) {

    setInterval(
        crearElementoNavidad,
        450
    );


    /*
     * Elementos iniciales
     */
    for (let i = 0; i < 15; i++) {

        setTimeout(
            crearElementoNavidad,
            Math.random() * 4000
        );

    }

}


/*
 * ==============================
 * MENSAJE THYMELEAF
 * ==============================
 */

const mensaje = document.getElementById("mensaje");

if (mensaje) {

    setTimeout(() => {

        window.location.reload();

    }, 3000);

}