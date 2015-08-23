//gerador Cartao VISA Adaptar outros Cartoes

def groovyUtils = new com.eviware.soapui.support.GroovyUtils(context)

def prefixList = ["4539", "4556", "4916", "4532", "4929", "40240071", "4485", "4716", "4" ].toArray()

def Stack<String> result = new Stack<String>()

def ccnumber 
def howMany = 10
def length = 16

for (int i = 0; i < howMany; i++) {
	def randomArrayIndex = (int) Math.floor(Math.random() * prefixList.length)
	ccnumber = prefixList[randomArrayIndex]
	//def ccnumber = "VISA"

	// generate digits
	while (ccnumber.length() < (length - 1)) {
		ccnumber += new Double(Math.floor(Math.random() * 10)).intValue();
	}

	// reverse number and convert to int
	def reversedCCnumberString = ""
	if (ccnumber == null){
		reversedCCnumberString = ""
	}
	
	for (int x = ccnumber.length() - 1; x >= 0; x--) {
		reversedCCnumberString += ccnumber.charAt(x);	
	}

	def List<Integer> reversedCCnumberList = new Vector<Integer>();

	for (int y = 0; y < reversedCCnumberString.length(); y++) {
		reversedCCnumberList.add(new Integer(String.valueOf(reversedCCnumberString.charAt(y))))
	}

	// calculate sum
	def sum = 0
	def pos = 0
	def reversedCCnumber = reversedCCnumberList.toArray(new Integer[reversedCCnumberList.size()])

	while (pos < length - 1) 	{
		def odd = reversedCCnumber[pos] * 2
		if (odd > 9) {
			odd -= 9;
		}

		sum += odd;
		if (pos != (length - 2)) {
			sum += reversedCCnumber[pos + 1]
		}
		pos += 2;
	}

	def checkdigit = new Double(((Math.floor(sum / 10) + 1) * 10 - sum) % 10).intValue()
	ccnumber += checkdigit;
}

testRunner.testCase.setPropertyValue("numeroCartao", "" + ccnumber)