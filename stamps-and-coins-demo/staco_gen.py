# Requiresa Python 3.5+
import json
from random import randint
from random import random

vowels = "aeiou"
consonants = "bcdfghjklmnpqrstvwxyz"


class Coin:
    def __init__(self, description, year, value, currency, diameter_mm, internal_diameter_mm):
        self.description = description
        self.year = year
        self.value = value
        self.type = "COIN"
        self.currency = currency
        self.diameterMM = diameter_mm
        self.internalDiameterMM = internal_diameter_mm

    def _get_bar(self):
        return self.__id

    def _set_id(self, value):
        if not isinstance(value, int):
            self.__id = value[0]
        else:
            self.__id = value

    id = property(_get_bar, _set_id)


class Stamp(object):
    def __init__(self, description, year, value, currency, height_mm, width_mm):
        self.description = description
        self.year = year
        self.value = value
        self.type = "STAMP"
        self.currency = currency
        self.heightMM = height_mm
        self.widthMM = width_mm

    def _get_bar(self):
        return self.__id

    def _set_id(self, value):
        if not isinstance(value, int):
            self.__id = value[0]
        else:
            self.__id = value

    id = property(_get_bar, _set_id)


def create_random_from_i(start, delta):
    return int(start + random() * delta)


def create_word():
    n = randint(3, 5)
    word = ""
    first_letter = randint(0, len(consonants) - 1)
    word += consonants[first_letter].upper()
    for x in range(1, n):
        v = randint(0, 2)
        if v == 0:
            word += vowels[randint(0, len(vowels) - 1)]
        else:
            word += consonants[randint(0, len(consonants) - 1)]
    return word


def create_comment():
    n = randint(4, 20)
    text = create_word()
    for k in range(1, n):
        text += " " + create_word()
    return text


if __name__ == '__main__':

    filename = 'sample.json'
    currencies = ["EUR", "USD", "JPY", "CHF", "PTE", "ESP"]
    all_stacos = []
    with open(filename, 'w') as file_object:
        for i in range(0, 100):
            diameter = create_random_from_i(5, 10)
            coin = Coin(create_comment(), create_random_from_i(1000, 1021), create_random_from_i(1, 100),
                        currencies[create_random_from_i(1, 5)], diameter, create_random_from_i(0, diameter - 2))
            coin.id = i + 1
            coin_dict = coin.__dict__
            coin_dict['id'] = coin_dict.pop('_Coin__id')
            all_stacos.append(coin_dict)
        for i in range(100, 200):
            stamp = Stamp(create_comment(), create_random_from_i(1000, 1021), create_random_from_i(1, 100),
                          currencies[int(random() * 5)], create_random_from_i(1, 10),
                          create_random_from_i(1, 10))
            stamp.id = i + 1
            stamp_dict = stamp.__dict__
            stamp_dict['id'] = stamp_dict.pop('_Stamp__id')
            all_stacos.append(stamp_dict)
        json.dump(all_stacos, file_object)
