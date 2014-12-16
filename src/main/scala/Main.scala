object Main extends App {

  case class Street(name: String)
  case class Address(street: Street)
  case class Company(address: Address)
  case class Employee(company: Company)

  val employee =
  Employee(Company(Address(Street("123 any street"))))

  val copied = employee.copy(
    company = employee.company.copy(
      address = employee.company.address.copy(
        street = employee.company.address.street.copy(
          name = employee.company.address.street.name.toUpperCase
          )
        )
      )
    )

  println(copied)

  // Another way ...

  import monocle._

  val _company = Lens[Employee, Company](_.company)( c => e => e.copy(company=c))

  val _address = Lens[Company, Address](_.address)( a => c => c.copy(address=a))

  val _street = Lens[Address, Street](_.street)( s => a => a.copy(street=s))

  val _name = Lens[Street, String](_.name)( n => s => s.copy(name=n))

  val lensed =
  (_company composeLens _address composeLens _street composeLens _name).modify(_.toUpperCase)(employee)

  println(lensed)

}