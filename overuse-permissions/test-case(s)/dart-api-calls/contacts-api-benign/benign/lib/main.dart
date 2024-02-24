import 'package:flutter/material.dart';
import 'package:contacts_service/contacts_service.dart';
import 'package:permission_handler/permission_handler.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Contact Reader',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: ContactScreen(),
    );
  }
}

class ContactScreen extends StatefulWidget {
  @override
  _ContactScreenState createState() => _ContactScreenState();
}

class _ContactScreenState extends State<ContactScreen> {
  List<Contact> _contacts = [];

  @override
  void initState() {
    super.initState();
    _loadContacts();
  }

  Future<void> _loadContacts() async {
    // Check if permission is granted
    PermissionStatus permissionStatus = await Permission.contacts.status;
    if(permissionStatus.isGranted) {
      Iterable<Contact> contacts =
      await ContactsService.getContacts(withThumbnails: false);
      setState(() {
        _contacts = contacts.toList();
      });
    } else {
      // Request permission if not granted
      PermissionStatus requestResult = await Permission.contacts.request();
      if (requestResult.isGranted) {
        // Permission granted, load contacts
        _loadContacts();
      } else {
        // Permission denied
        print('Permission denied');
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Contacts'),
      ),
      body: ListView.builder(
        itemCount: _contacts.length,
        itemBuilder: (context, index) {
          Contact contact = _contacts[index];
          String phoneNumber = '';
          if (contact.phones?.isNotEmpty ?? false) {
            phoneNumber = contact.phones!.first.value ?? 'No phone number';
          } else {
            phoneNumber = 'No phone number';
          }
          return ListTile(
            title: Text(contact.displayName ?? ''),
            subtitle: Text(phoneNumber),
          );
        },
      ),
    );
  }
}
