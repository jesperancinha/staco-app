# Requiresa Python 3.5+
import json

from pandas.io.json import to_json


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
        for i in range(1, 100):
            all_stacos.append(Coin(1, "aeioiegbiaauei aeioiegbiaauei", 1996, 15, "EUR", 10, 0).__dict__)
        for i in range(1, 100):
            all_stacos.append(Stamp(1, "aeioiegbiaauei aeioiegbiaauei", 1996, 15, "EUR", 10, 0).__dict__)
        json.dump(all_stacos, file_object)
