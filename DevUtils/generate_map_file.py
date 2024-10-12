import json

from PIL import Image

IMG_MAP_PATH = "map.png"

COLOUR_MAPPINGS = {(0, 255, 0): "GRASS", (0, 0, 255): "WATER"}

# Open the image file
image = Image.open(IMG_MAP_PATH)
image = image.convert("RGB")

width, height = image.size

extracted_map = []

# Iterate through each pixel going from bottom right
for y in range(height):
    extracted_map.append([])

    for x in range(width):
        # Add the tile with specified mapping to the array
        col = image.getpixel((x, y))
        extracted_map[y].append(COLOUR_MAPPINGS[col])

# Write the map into a file
with open("map.json", "w") as f:
    json.dump({"map": extracted_map}, f)
