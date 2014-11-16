#!/bin/sh
npm install -g yuidocjs
rm -r doc/client/ && yuidoc -T simple -x resources/public/js/lib -o doc/client resources/public/js/
