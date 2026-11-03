# App: ImageButtonLlamar
# Proyecto del documento: Proyecto007 - Control ImageButton
# Integrantes (Grupo 1):
#   Eymer Baez - Matrícula: 1-24-3833
#   Ebelio Francisco - Matrícula: 1-24-3647
#   Enrique Rincon - Matrícula: 1-24-3689
#   Angel Ventura - Matrícula: 1-24-3690
#
# Dibuja la imagen telefono.png (teléfono de mesa blanco sobre un círculo verde UTESA)
# con Pillow. Cada densidad se dibuja directamente a su tamaño a partir de la misma
# geometría (no se estira la imagen de 50x50). Para suavizar los bordes, cada tamaño
# se dibuja primero a 8 veces su resolución y luego se reduce.
#
# Uso: python3 generar_telefono.py <carpeta res del proyecto>

import os
import sys

from PIL import Image, ImageDraw

VERDE_UTESA = (7, 102, 51, 255)  # #076633
BLANCO = (255, 255, 255, 255)
SUPERMUESTREO = 8

# Tamaños de la sección "Comentarios extras" del documento (mdpi = 100%).
DENSIDADES = {
    "drawable-ldpi": 38,     # 75%
    "drawable-mdpi": 50,     # 100% (tamaño base del documento)
    "drawable-hdpi": 75,     # 150%
    "drawable-xhdpi": 100,   # 200%
    "drawable-xxhdpi": 150,  # 300%
}


def dibujar_telefono(tamano):
    """Dibuja telefono.png de tamano x tamano px."""
    lado = tamano * SUPERMUESTREO
    u = lado / 100.0  # unidad: 1/100 del lado

    def caja(x1, y1, x2, y2):
        return (x1 * u, y1 * u, x2 * u, y2 * u)

    imagen = Image.new("RGBA", (lado, lado), (0, 0, 0, 0))
    d = ImageDraw.Draw(imagen)

    # Fondo: círculo verde UTESA que ocupa todo el cuadro.
    d.ellipse((0, 0, lado - 1, lado - 1), fill=VERDE_UTESA)

    # Auricular apoyado sobre la base: mango horizontal y dos almohadillas hacia abajo.
    d.rounded_rectangle(caja(22, 22, 78, 32), radius=5 * u, fill=BLANCO)
    d.rounded_rectangle(caja(17, 22, 35, 41), radius=6 * u, fill=BLANCO)
    d.rounded_rectangle(caja(65, 22, 83, 41), radius=6 * u, fill=BLANCO)

    # Base del teléfono: trapecio (más ancho abajo) con las esquinas inferiores redondeadas.
    d.polygon([(33 * u, 44 * u), (67 * u, 44 * u), (80 * u, 74 * u), (20 * u, 74 * u)], fill=BLANCO)
    d.rounded_rectangle(caja(19, 66, 81, 79), radius=5 * u, fill=BLANCO)

    # Disco de marcar: anillo verde con el centro blanco.
    d.ellipse(caja(38, 49, 62, 73), fill=VERDE_UTESA)
    d.ellipse(caja(45, 56, 55, 66), fill=BLANCO)

    return imagen.resize((tamano, tamano), Image.LANCZOS)


def main():
    res = sys.argv[1]
    for carpeta, tamano in DENSIDADES.items():
        destino = os.path.join(res, carpeta)
        os.makedirs(destino, exist_ok=True)
        ruta = os.path.join(destino, "telefono.png")
        dibujar_telefono(tamano).save(ruta, optimize=True)
        print(f"{ruta}: {tamano}x{tamano}")


if __name__ == "__main__":
    main()
