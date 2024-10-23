import argparse
import json

from PIL import Image

COLOUR_MAPPINGS = {(0, 255, 0): "GRASS", (0, 0, 255): "WATER"}


def create_map(in_img, out_json):
    # Open the image file
    image = Image.open(in_img)
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
    with open(out_json, "w") as f:
        json.dump({"map": extracted_map}, f)


# Parse arguments to get the input image file and output json file names
parser = argparse.ArgumentParser()
parser.add_argument("input_path", type=str, help="Input Image Path")
parser.add_argument("-o", "--out", type=str, help="Output JSON Path", default="map.json")
args = parser.parse_args()


create_map(args.input_path, args.out)
