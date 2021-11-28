# Requiresa Python 3.5+
import json
from random import random


class Coin:
    def __init__(self, _id, description, year, value, currency, diameter_mm, internal_diameter_mm):
        self.id = 10
        self.id = _id
        self.description = description
        self.year = year
        self.value = value
        self.type = "COIN"
        self.currency = currency
        self.diameterMM = diameter_mm
        self.internalDiameterMM = internal_diameter_mm


class Stamp:
    def __init__(self, _id, description, year, value, currency, height_mm, width_mm):
        self.id = 10
        self.id = _id,
        self.description = description
        self.year = year
        self.value = value
        self.type = "STAMP"
        self.currency = currency
        self.heightMM = height_mm
        self.witdthMM = width_mm


if __name__ == '__main__':

    filename = 'sample.json'

    all_stacos = []
    with open(filename, 'w') as file_object:
        count = 1
        for i in range(1, 100):
            all_stacos.append(
                Coin(count, "aeioiegbiaauei aeioiegbiaauei", int(1000 + random() * 1021), int(1 + random() * 100),
                     "EUR",
                     int(1 + random() * 10), int(1 + random() * 10)).__dict__)
            count += 1
        for i in range(1, 100):
            all_stacos.append(
                Stamp(count, "aeioiegbiaauei aeioiegbiaauei", int(1000 + random() * 1021), 15, "EUR",
                      int(1 + random() * 10), int(1 + random() * 10)).__dict__)
            count += 1
        json.dump(all_stacos, file_object)
